# Pocket Forecaster

An AI-powered smartphone recommendation system built with Java and hybrid sentiment analysis.

## Demo

![Demo](https://raw.githubusercontent.com/macrosensor2022/pocket_forecaster/main/assets/demo.gif)

[Full demo video](https://raw.githubusercontent.com/macrosensor2022/pocket_forecaster/main/demo.mp4)

## Overview

Pocket Forecaster recommends smartphones based on your budget, usage type (gaming, photography, casual, etc.), and OS preference. It uses a hybrid sentiment analysis model (VADER + BERT) to analyze real customer reviews and provide confidence scores for each recommendation.

Built using Java with MVC architecture and Strategy Design Pattern.

## Project Structure

```
pocket_forecaster/
├─ src/
├─ Test/
├─ documentation/
│     └─ Pocket_forecaster_final_document.pdf
├─ Sent_analysis_for_reviews_hybrid.ipynb
├─ pom.xml
├─ README.md
├─ demo.mp4
└─ .gitignore
```

## Features

**Filtering:**
- Budget-based filtering
- Usage type (Gaming, Photography, Casual, Performance)
- OS preference (Android/iOS)

**Sentiment Analysis:**
- Hybrid model using VADER + BERT
- Analyzes customer reviews
- Provides sentiment percentage and summary

**Personalized Add-ons:**
- Recommends accessories, apps, and games based on usage type

**Architecture:**
- MVC Pattern
- Strategy Design Pattern for different usage types
- JUnit tests included

**Frontend:**
- Web interface with HTML, CSS, JavaScript

## Tech Stack

- **Backend:** Java, Spark Java API, Apache Commons CSV
- **Frontend:** HTML, CSS, JavaScript
- **AI:** Python (VADER & BERT sentiment analysis)
- **Data:** CSV files with specs, reviews, sentiment scores

## How It Works

```
1. User inputs budget, usage type, and OS preference
2. System filters phones using Strategy Pattern
3. Sentiment analysis from VADER + BERT is attached
4. Top recommendations shown with sentiment scores
5. Personalized add-ons suggested
```

## Example

For a gaming user with ₹80,000 budget on Android:
- ASUS ROG Phone 8 (₹74,999) - 89% Positive
- OnePlus 12 (₹59,999) - 84% Positive
- Samsung Galaxy S23 (₹69,999) - 90% Positive

Plus gaming accessories and app recommendations.

## Documentation

Full documentation: [PDF](documentation/Pocket_forecaster_final_document.pdf)

## Future Ideas

- Real-time pricing API
- User accounts
- Improved sentiment models
- Cloud deployment
